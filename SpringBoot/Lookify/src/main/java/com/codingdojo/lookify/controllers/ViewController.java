package com.codingdojo.lookify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/dashboard")
    public String dashboard(){

        return "dashboard";
    }
    @GetMapping("/search/{artista}")//Para no usar esto porque parece una redundancia al utilizar la logica en el front podría hacer una SPA y cambiar la ruta solamente
    public String search(@PathVariable("artista") String artista){

        return "search";
    }
    @GetMapping("/songs/topTen")
    public String search(){

        return "topten";
    }
    @GetMapping("/songs/new")
    public String agregar(){
        return "add";
    }
    @GetMapping("/songs/{id}")
    public String detalle(@PathVariable("id") Long id){
        return "detail";
    }
}
