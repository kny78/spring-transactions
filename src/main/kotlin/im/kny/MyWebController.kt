package im.kny

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.io.BufferedInputStream
import java.io.FileInputStream

@RestController
@RequestMapping("/mydata")
class MyWebController @Autowired constructor() {


    @GetMapping("/other")
    fun getMyData(): ResponseEntity<Mono<InputStreamResource>> {
        return ResponseEntity
            .ok()
            .body(
                getInputStream()
                    .flatMap { x ->
                        println("Mono.just()")
                        Mono.just(InputStreamResource(x))
                    }
            )
    }


    private fun getInputStream(): Mono<BufferedInputStream> {
        return Mono
            .fromCallable {
                BufferedInputStream(FileInputStream("/home/kny/Downloads/dump.txt"))
            }
    }
}