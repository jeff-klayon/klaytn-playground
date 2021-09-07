package com.krustuniverse.klayon.playground

import com.klaytn.caver.methods.response.AccountSmartContract
import com.klaytn.caver.methods.response.AccountTypeEOA
import com.klaytn.caver.methods.response.IAccountType
import java.math.BigInteger

data class KlaytnAccount(val balance: BigInteger) {
    companion object {
        fun from(account: IAccountType) = if (account.type == IAccountType.AccType.EOA) {
            KlaytnAccount(parse((account as AccountTypeEOA).balance))
        } else {
            KlaytnAccount(parse((account as AccountSmartContract).balance))
        }

        private fun parse(balance: String) = if (balance.startsWith("0x")) {
            BigInteger(balance.removePrefix("0x"), 16)
        } else {
            BigInteger(balance, 16)
        }
    }
}