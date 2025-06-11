package com.travelvn.tourbookingsytem.controller.firestore

import com.travelvn.tourbookingsytem.service.FirestoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class FirestoreController(
    @Autowired private val firestoreService: FirestoreService
) {

    @PostMapping("/add-customer")
    fun addCustomerToChat(
        @RequestParam tourUnitId: String,
        @RequestParam customerId: Int
    ): ResponseEntity<String> {
        return try {
            if (tourUnitId.isBlank()) {
                return ResponseEntity.badRequest().body("tourUnitId không được để trống.")
            }
            firestoreService.addCustomerIdToChat(
                tourUnitId = tourUnitId,
                customerId = customerId
            )
            ResponseEntity.ok("Thêm khách hàng vào nhóm chat thành công.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body("Lỗi: ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi thêm khách hàng: ${e.localizedMessage}")
        }
    }

    @PostMapping("/add-tour-guide")
    fun addTourGuideToChat(
        @RequestParam tourUnitId: String,
        @RequestParam tourGuideId: Int
    ): ResponseEntity<String> {
        return try {
            if (tourUnitId.isBlank()) {
                return ResponseEntity.badRequest().body("tourUnitId không được để trống.")
            }
            firestoreService.addTourGuideIdToChat(
                tourUnitId = tourUnitId,
                tourGuideId = tourGuideId
            )
            ResponseEntity.ok("Thêm hướng dẫn viên vào nhóm chat thành công.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body("Lỗi: ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi thêm hướng dẫn viên: ${e.localizedMessage}")
        }
    }
}