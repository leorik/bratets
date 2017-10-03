package org.rmgsoc.bratets.models.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BroRepository : CrudRepository<Bro, Long>

@Repository
interface BroResponseRepository : CrudRepository<BroResponse, Long>