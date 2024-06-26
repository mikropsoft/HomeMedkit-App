package ru.application.homemedkit.helpers

import android.content.Context
import android.widget.Toast
import ru.application.homemedkit.R
import ru.application.homemedkit.databaseController.Intake
import ru.application.homemedkit.databaseController.Medicine
import ru.application.homemedkit.databaseController.MedicineDatabase
import java.util.Locale

class FiltersHelper(private val context: Context) {

    private val database: MedicineDatabase = MedicineDatabase.getInstance(context)

    fun medicines(text: String): List<Medicine> {
        val medicines = database.medicineDAO().getAll()
        val filtered = ArrayList<Medicine>(medicines.size)

        if (text.isEmpty()) {
            filtered.addAll(medicines)
        } else {
            for (medicine in medicines) {
                val productName = database.medicineDAO().getProductName(medicine.id)
                if (productName.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                    filtered.add(medicine)
                }
            }
        }

        if (text.isNotEmpty() && filtered.isEmpty())
            Toast.makeText(context, R.string.text_no_data_found, Toast.LENGTH_SHORT).show()

        return filtered
    }

    fun intakes(text: String): List<Intake> {
        val intakes = database.intakeDAO().getAll()
        val filtered = ArrayList<Intake>(intakes.size)

        if (text.isEmpty()) {
            filtered.addAll(intakes)
        } else {
            for (medicine in intakes) {
                val productName = database.medicineDAO().getProductName(medicine.medicineId)
                if (productName.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                    filtered.add(medicine)
                }
            }
        }

        if (text.isNotEmpty() && filtered.isEmpty())
            Toast.makeText(context, R.string.text_no_data_found, Toast.LENGTH_SHORT).show()

        return filtered
    }
}