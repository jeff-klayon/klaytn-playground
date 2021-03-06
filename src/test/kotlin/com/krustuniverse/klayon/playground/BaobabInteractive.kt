package com.krustuniverse.klayon.playground

import com.klaytn.caver.Caver
import com.klaytn.caver.transaction.response.PollingTransactionReceiptProcessor
import com.klaytn.caver.transaction.type.ValueTransfer
import com.klaytn.caver.utils.Utils
import com.klaytn.caver.wallet.keyring.AbstractKeyring
import com.klaytn.caver.wallet.keyring.KeyringFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.web3j.protocol.core.Response
import java.math.BigDecimal
import java.math.BigInteger

class BaobabInteractive {
    private val user1 = UserCredential.load("./baobab_test_user1.json")
    private val user2 = UserCredential.load("./baobab_test_user2.json")

    private val baobabEndpointUrl = "https://api.baobab.klaytn.net:8651"
    private val caver = Caver(baobabEndpointUrl)

    @Test
    fun transfer() {
        val keyring: AbstractKeyring = KeyringFactory.createFromPrivateKey(user1.privateKey)

        caver.wallet.add(keyring)

        val accountBefore = findAccount(user2.walletAddress)

        // 0.01 KLAY
        val value = BigInteger(Utils.convertToPeb(BigDecimal.valueOf(10), Utils.KlayUnit.mKLAY))

        val valueTransfer: ValueTransfer = ValueTransfer.Builder()
                .setKlaytnCall(caver.rpc.klay)
                .setFrom(keyring.address)
                .setTo(user2.walletAddress)
                .setValue(value)
                .setGas(BigInteger.valueOf(25000))
                .build()

        //Sign to the transaction
        valueTransfer.sign(keyring)

        //Send a transaction to the klaytn blockchain platform (Klaytn)
        val transferResponse = caver.rpc.klay.sendRawTransaction(valueTransfer.rawTransaction).send()
        checkResponse(transferResponse)

        //Check transaction receipt.
        val transactionReceipt = PollingTransactionReceiptProcessor(caver, 1000, 15).waitForTransactionReceipt(transferResponse.result)

        val accountAfter = findAccount(user2.walletAddress)

        assertEquals(accountBefore.balance + value, accountAfter.balance)
    }

    @Test
    fun findTransaction() {
        val txHash = "0x58666cac6068d07667da1aaeb4f8d3d22a0a98841539ed9a299313b3d18f3cba"
        val txResponse = caver.rpc.klay.getTransactionByHash(txHash).send()
        checkResponse(txResponse)

        assertEquals(txHash, txResponse.result.hash)
    }

    private fun findAccount(walletAddress: String): KlaytnAccount {
        val accountResponse = caver.rpc.klay.getAccount(walletAddress).send()
        checkResponse(accountResponse)
        return KlaytnAccount.from(accountResponse.result.account)
    }

    private fun <T> checkResponse(response: Response<T>) {
        if (response.hasError()) {
            throw RuntimeException(response.error.message)
        }
    }
}
