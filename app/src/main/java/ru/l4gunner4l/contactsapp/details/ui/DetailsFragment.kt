package ru.l4gunner4l.contactsapp.details.ui

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*
import ru.l4gunner4l.contactsapp.R
import ru.l4gunner4l.contactsapp.base.model.ContactModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailsFragment : DaggerFragment(R.layout.fragment_details) {

    companion object {
        private const val KEY_CONTACT_ID = "KEY_CONTACT_ID"
        private const val REQUEST_TAKE_IMAGE_FROM_CAMERA = 123
        private const val REQUEST_TAKE_IMAGE_FROM_STORAGE = 124

        fun newInstance(id: String? = null) =
            DetailsFragment().apply {
                arguments = Bundle().apply { putString(KEY_CONTACT_ID, id) }
            }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var photoPath: String = ""

    private var isCreateMode: Boolean = true
    private val viewModel: DetailsViewModel by viewModels<DetailsViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        requireArguments().getString(KEY_CONTACT_ID).let { id ->
            isCreateMode = id == null
            viewModel.id = id
            if (!isCreateMode)
                viewModel.processDataEvent(DataEvent.RequestContact(id!!))
        }
        initUi()
    }

    private fun initUi() {
        detailsBtnTakePhoto.setOnClickListener { showTakePhotoDialog() }
        detailsImage.setOnLongClickListener { showDeletePhotoDialog(); true }
        btnSave.setOnClickListener { onSaveClick() }
        with(detailsToolbar) {
            title = if (isCreateMode) "Create contact" else "Edit contact"
            setNavigationIcon(R.drawable.ic_back_24)
            setNavigationOnClickListener { viewModel.processUiEvent(UiEvent.OnExitClick) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode != REQUEST_TAKE_IMAGE_FROM_CAMERA && requestCode != REQUEST_TAKE_IMAGE_FROM_STORAGE) || resultCode != RESULT_OK || data == null) return
        if (requestCode == REQUEST_TAKE_IMAGE_FROM_CAMERA) updatePhoto()
        if (requestCode == REQUEST_TAKE_IMAGE_FROM_STORAGE) updatePhoto(data.data)
    }

    private fun takePhotoFromStorage() {
        Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
            .let { intent ->
                intent.resolveActivity(requireActivity().packageManager)?.let {
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        REQUEST_TAKE_IMAGE_FROM_STORAGE
                    )
                }
            }
    }

    private fun takePhotoFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(), "ru.l4gunner4l.contactsapp.fileprovider", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_IMAGE_FROM_CAMERA)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            photoPath = absolutePath
        }
    }

    private fun onSaveClick() {
        val id = arguments?.getString(KEY_CONTACT_ID)
        if (!isCreateMode) {
            viewModel.processUiEvent(
                DataEvent.UpdateContact(
                    ContactModel(
                        id!!,
                        photoPath,
                        detailsTvName.text.toString(),
                        detailsTvPhone.text.toString()
                    )
                )
            )
        } else {
            viewModel.processUiEvent(
                DataEvent.CreateContact(
                    ContactModel(
                        UUID.randomUUID().toString(),
                        photoPath,
                        detailsTvName.text.toString(),
                        detailsTvPhone.text.toString()
                    )
                )
            )
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.CONTENT -> {
                setContactToUi(viewState.contact)
            }
            STATUS.LOAD -> {

            }
            STATUS.ERROR -> {

            }
        }
    }

    private fun showTakePhotoDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Where from you want to take photo?")
            setPositiveButton("Camera") { _, _ -> takePhotoFromCamera() }
            setNeutralButton("Storage") { _, _ -> takePhotoFromStorage() }
        }.show()
    }

    private fun showDeletePhotoDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Do you really want to delete photo?")
            .setPositiveButton("Yes") { _, _ -> updatePhoto(null) }
            .setNegativeButton("No") { d, _ -> d.dismiss() }
            .show()
    }

    private fun updatePhoto() {
        Glide.with(requireContext())
            .load(photoPath)
            .into(detailsImage)
    }

    private fun updatePhoto(image: Uri?) {
        photoPath = image?.toString() ?: ""
        Glide.with(requireContext())
            .load(image ?: R.drawable.ic_baseline_person_24)
            .into(detailsImage)
    }

    private fun setContactToUi(contact: ContactModel?) {
        contact?.let {
            Glide.with(this)
                .load(if (it.imagePath.isNotBlank()) it.imagePath else R.drawable.ic_baseline_person_24)
                .into(detailsImage)
            detailsTvName.setText(it.name)
            detailsTvPhone.setText(it.phone)
        }
    }
}