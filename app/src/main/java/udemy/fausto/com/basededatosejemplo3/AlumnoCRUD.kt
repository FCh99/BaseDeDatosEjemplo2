package udemy.fausto.com.basededatosejemplo3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class AlumnoCRUD (context: Context){
    private var helper: DataBaseHelper? = null

    init {
        helper = DataBaseHelper(context)
    }

    fun newAlumno(item: Alumno) {

        // abrir db en modo escritura
        val db = helper?.writableDatabase!!

        // mapeo columnas - valores a insertar
        val values = ContentValues()
        values.put(AlumnosContract.Companion.Entrada.COLUMNA_ID, item.id)
        values.put(AlumnosContract.Companion.Entrada.COLUMNA_NOMBRE, item.name)

        // insertar una fila en la tabla
        val newRowId = db.insert(AlumnosContract.Companion.Entrada.NOMBRE_TABLA, null, values)

        db.close()

    }

    // Traer todos los alumnos

    fun getAlumnos() : ArrayList<Alumno>{
        val items: ArrayList<Alumno> = ArrayList()

        // abrir db lectura
        val db = helper?.readableDatabase!!

        // especificar las 2 columnas que quiero consultar
        val columnas = arrayOf(AlumnosContract.Companion.Entrada.COLUMNA_ID, AlumnosContract.Companion.Entrada.COLUMNA_NOMBRE)

        // crea un cursor para recorrer la tabla
        val c = db.query(
            AlumnosContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            null,
            null,
            null,
            null,
            null
        )

        // hacer el recorrido del cursor en la tabla
        while (c.moveToNext()) {
            items.add(Alumno(
                c.getString(c.getColumnIndexOrThrow(AlumnosContract.Companion.Entrada.COLUMNA_ID)),
                c.getString(c.getColumnIndexOrThrow(AlumnosContract.Companion.Entrada.COLUMNA_NOMBRE))
            ))
        }

        // cerrar db y retornar arrayList
        db.close()
        return items

    }

    fun getAlumno(id: String): Alumno {
        var item: Alumno? = null

        val db = helper?.readableDatabase!!


        val columnas = arrayOf(AlumnosContract.Companion.Entrada.COLUMNA_ID, AlumnosContract.Companion.Entrada.COLUMNA_NOMBRE)

        val c: Cursor = db.query(
            AlumnosContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            "id = ?",
            arrayOf(id),
            null,
            null,
            null

        )
        while (c.moveToNext()) {
            item = Alumno(c.getString(c.getColumnIndexOrThrow(AlumnosContract.Companion.Entrada.COLUMNA_ID)), c.getString(c.getColumnIndexOrThrow(AlumnosContract.Companion.Entrada.COLUMNA_NOMBRE)))
        }

        c.close()
        return item!!


    }

    fun updateAlumno(item: Alumno) {
        val db = helper?.writableDatabase!!

        val values = ContentValues()
        values.put(AlumnosContract.Companion.Entrada.COLUMNA_ID, item.id)
        values.put(AlumnosContract.Companion.Entrada.COLUMNA_NOMBRE, item.name)

        db.update(AlumnosContract.Companion.Entrada.NOMBRE_TABLA, values, "id = ?",
            arrayOf(item.id)
            )
        db.close()


    }

    fun deleteAlumno(item: Alumno) {
        val db: SQLiteDatabase = helper?.writableDatabase!!
        db.delete(AlumnosContract.Companion.Entrada.NOMBRE_TABLA, "id = ?", arrayOf(item.id) )

        db.close()





    }


}