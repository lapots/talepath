package com.lapots.breed.backend

import com.lapots.breed.backend.mock.MockDataService
import spock.lang.Specification

class DataServiceSpecification extends Specification {

    def service

    def setup() {
        service = MockDataService.getInstance()
    }

    def "should return products"() {
        when:
            def products = service.getAllProducts()
        then:
            assert products != []
    }

    def "should return categories"() {
        when:
            def categories = service.getAllCategories()
        then:
            assert categories != []
    }

    def "should update product"() {
        given:
            def name = "My Test Name"
            def p = service.getAllProducts()[0]
        when:
            p.productName = name
            service.updateProduct(p)
        then:
            assert name == service.getAllProducts()[0].productName
    }
}
