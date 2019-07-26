package com.example.myfirebaseauthorization

class DataUser() {
    var key: String = ""
    var name: String = ""
    var age: String = ""


    constructor(key: String, name: String, age: String) : this() {
        this.key = key
        this.name = name
        this.age = age
    }

    constructor(name: String, age: String) : this() {
        this.name = name
        this.age = age
    }
}