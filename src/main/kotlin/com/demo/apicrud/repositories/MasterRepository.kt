package com.demo.apicrud.repositories

import com.demo.apicrud.entities.Master
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterRepository : JpaRepository<Master, Long>