package ru.teacherarmy.check

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles

fun Any.retrofit(): TestFile.BinaryTestFile =
    TestFiles.bytes(
        "libs/retrofit-2.9.0.jar",
        javaClass
            .getResourceAsStream("/retrofit-2.9.0.jar")
            .readBytes(),
    )

fun Any.gson(): TestFile.BinaryTestFile =
    TestFiles.bytes(
        "libs/gson-2.8.8.jar",
        javaClass
            .getResourceAsStream("/gson-2.8.8.jar")
            .readBytes(),
    )
