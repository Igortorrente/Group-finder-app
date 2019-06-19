package com.example.groupfinder.userinterfaces.enums

enum class Caller { TIME_INIT, TIME_END, DATE_INIT, DATE_END }
enum class Mode{ ADMIN , USER }
enum class State{ VIEW, EDIT, INSIDE, OUTSIDE}
enum class RequestCode(val number: Int){
    NEW_GROUP(1), PROFILE_EDIT(2), GROUP(3)
}