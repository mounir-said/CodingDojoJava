console.log("js topten vinculado")
const url = "/api/canciones/topTen"
let contenidoHTML = ""
const tabla = document.querySelector(".tabla")
const pedirTopTen = async () => {
    try {
        const respuesta = await fetch(url)
        const datos = await respuesta.json()
        console.log(datos)
        datos.forEach(cancion =>{
            contenidoHTML += `<div>
                                <span class="rank">${cancion.clasificacion}</span> -
                                <a class="rank-name" href="/songs/${cancion.id}">${cancion.name}</a> -
                                <span class="rank-artista">${cancion.artista}</span> 
                              </div><hr>`
        })
        tabla.innerHTML = contenidoHTML //Para hacerle un appendchild tengo que tener parametro node en contenido html y es un string!

    } catch (error) {
        console.log(error)
    }
}
pedirTopTen()