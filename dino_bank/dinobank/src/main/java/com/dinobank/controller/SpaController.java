package com.dinobank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * =============================================================================
 * SPA CONTROLLER - Tek Sayfa Uygulama Yönlendirme Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, React gibi frontend framework'lerinin "client-side routing"
 * mekanizmasını desteklemek için kritik bir göreve sahiptir.
 * 
 * PROBLEM:
 * - React Router gibi kütüphaneler, tarayıcı URL'sini JavaScript ile değiştirir
 * - Kullanıcı /dashboard veya /credit-application gibi bir URL'e doğrudan
 * girerse,
 * Spring Boot bu yolu aramaya çalışır ve 404 hatası verir
 * 
 * ÇÖZÜM:
 * - Statik dosya olmayan tüm istekleri index.html'e yönlendiriyoruz
 * - React Router devreye girerek doğru komponenti render eder
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */
@Controller
public class SpaController {

    /**
     * SPA Yönlendirme Metodu
     * 
     * Bu metod, API olmayan ve dosya uzantısı içermeyen tüm GET isteklerini
     * index.html'e yönlendirir.
     * 
     * REGEX ACIKLAMASI: path degiskeni nokta icermeyen segmentleri yakalar
     * - Herhangi bir alt yol (orn: /dashboard, /settings/profile)
     * - Nokta (.) icermeyen herhangi bir segment
     * - Bu sayede .js, .css, .png gibi statik dosyalar etkilenmez
     * 
     * ORNEK SENARYOLAR:
     * - /dashboard -> index.html'e yonlendirilir
     * - /credit-application -> index.html'e yonlendirilir
     * - /script.js -> Oldugu gibi sunulur (statik dosya)
     * - /api/credits -> Bu controller'i etkilemez (API endpoint)
     * 
     * @return forward:/index.html - Istek index.html'e iletilir
     */
    @RequestMapping(method = { RequestMethod.GET }, value = "/**/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
