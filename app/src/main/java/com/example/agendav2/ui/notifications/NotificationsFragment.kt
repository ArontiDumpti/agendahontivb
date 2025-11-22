package com.example.agendav2.ui.notifications

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.agendav2.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var prefs: SharedPreferences
    private val PREFS_NAME = "agenda_prefs"
    private val NOTES_KEY = "notifications_notes"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load saved notes
        binding.editNotes.setText(prefs.getString(NOTES_KEY, ""))

        // Save button (explicit save)
        binding.btnSave.setOnClickListener {
            saveNotes()
            Toast.makeText(requireContext(), "Notes saved", Toast.LENGTH_SHORT).show()
        }

        // Optional: clear button
        binding.btnClear.setOnClickListener {
            binding.editNotes.setText("")
            saveNotes()
        }

        return root
    }

    // Auto-save when fragment goes to background
    override fun onPause() {
        super.onPause()
        saveNotes()
    }

    private fun saveNotes() {
        val text = binding.editNotes.text?.toString() ?: ""
        prefs.edit().putString(NOTES_KEY, text).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}