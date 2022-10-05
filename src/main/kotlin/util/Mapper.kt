package util

import database.entity.AccountsEntity
import server.model.common.Account

fun AccountsEntity.toServerAccount() = Account(
    id = id.value.toString(),
    email = email,
    name = name,
    password = password,
    reviewsCount = reviewsCount
)