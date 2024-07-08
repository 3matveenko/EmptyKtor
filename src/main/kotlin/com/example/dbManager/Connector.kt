package com.example.dbManager

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.statement.OutParameters
import java.sql.Types

class Connector{

    private fun setupDatabase(): Jdbi {
        return Jdbi.create("jdbc:sqlserver://WIN-QCQTDAFUUMM:1433;databaseName=testDB", "root", "root")
    }

    fun fetchDataFromDatabaseProcedure(): String? {
        val jdbi = setupDatabase()

        return jdbi.withHandle<String, RuntimeException> { handle ->
            handle.createCall("{CALL GetEmployeeFirstName(:EmployeeID)}").use { call ->
                call.bind("EmployeeID", 1)
                val output: OutParameters = call.invoke()

                val name = output.getObject(0, String::class.java)
                return@use name
            }
        }
    }

     fun fetchDataFromDatabaseQuery(): List<String> {
        val jdbi = setupDatabase()

        return jdbi.withHandle<List<String>, RuntimeException> { handle ->
            handle.createQuery("SELECT FirstName FROM Employees Where EmployeeID = 1")
                .mapTo(String::class.java)
                .list()
        }
    }

    //хранимая процедура
//    -- =============================================
//    -- Author:		<Author,,Name>
//    -- Create date: <Create Date,,>
//    -- Description:	<Description,,>
//    -- =============================================
//    CREATE PROCEDURE GetEmployeeFirstName
//    @EmployeeID INT
//    AS
//    BEGIN
//    SELECT FirstName FROM Employees WHERE EmployeeID = @EmployeeID;
//    END

//проверка через query в самом sql
//EXEC GetEmployeeFirstName @EmployeeID = 1;
}