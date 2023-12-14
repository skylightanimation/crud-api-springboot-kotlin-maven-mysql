package com.demo.apicrud.controllers

import com.demo.apicrud.entities.Master
import com.demo.apicrud.repositories.MasterRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: MasterRepository) {

    @GetMapping("/articles")
    fun getAllArticles(): List<Master> =
            articleRepository.findAll()


    @PostMapping("/articles")
    fun createNewArticle(@Valid @RequestBody article: Master): Master =
            articleRepository.save(article)


    @GetMapping("/articles/{id}")
    fun getArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Master> {
        return articleRepository.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/articles/{id}")
    fun updateArticleById(@PathVariable(value = "id") articleId: Long,
                          @Valid @RequestBody newArticle: Master): ResponseEntity<Master> {

        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: Master = existingArticle
                    .copy(name = newArticle.name, deleted = newArticle.deleted)
            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/articles/{id}")
    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {

        return articleRepository.findById(articleId).map { article  ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}