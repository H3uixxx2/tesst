package com.mongodb.tasktracker.model

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required

open class User(
    @PrimaryKey @RealmField("_id") var id: String = "",
    var userId: String = "",
    var username: String = "",
    var password: String = "", // Lưu ý: không nên lưu trữ mật khẩu trong cơ sở dữ liệu
    var role: String = "",
    var details: UserDetails? = null,
    var memberOf: RealmList<Project> = RealmList()
) : RealmObject()

open class UserDetails(
    var name: String = "",
    var email: String = ""
) : RealmObject()
