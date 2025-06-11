package com.travelvn.tourbookingsytem.service

import com.google.cloud.Timestamp
import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.Firestore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class FirestoreService(
    @Autowired private val firestore: Firestore
) {

    private val ref = firestore.collection("chats")

    fun addCustomerIdToChat(tourUnitId: String, customerId: Int) {
        try {
            ref.document(tourUnitId)
                .update("participants", FieldValue.arrayUnion(customerId))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addTourGuideIdToChat(tourUnitId: String, tourGuideId: Int) {
        try {
            ref.document(tourUnitId).update(
                mapOf(
                    "tourGuideId" to FieldValue.arrayUnion(tourGuideId)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    @Throws(java.lang.Exception::class)
    fun cleanupInactiveChats() {
        // Tính timestamp của 7 ngày trước
        val millis7DaysAgo = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
        val threshold = Timestamp.of(Date(millis7DaysAgo))

        // Truy vấn các nhóm chat có lastTimestamp nhỏ hơn threshold
        val future = firestore.collection("chats")
            .whereLessThan("lastTimestamp", threshold)
            .get()

        val inactiveChats = future.get().documents

        for (chatDoc in inactiveChats) {
            firestore.collection("chats").document(chatDoc.id).delete()
        }
    }
}