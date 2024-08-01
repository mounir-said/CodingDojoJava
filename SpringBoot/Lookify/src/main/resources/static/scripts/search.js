console.log("js search vinculado")

const pathname = window.location.pathname;
const parts = pathname.split('/');
const artista = parts[2];

console.log("Se busca por artista con", artista);
document.querySelector(".titulo-busqueda").innerHTML += decodeURIComponent(artista)
const url = `/api/canciones/busqueda/${artista}`

let cuerpo = document.querySelector(".cuerpo")
let contenidoHTML = ""
let isGray = true

//Tengo código redundante, debo perfeccionar el import y export de funciones para reutilizarlas pero me va a llevar mas tiempo y quiero cumplir con la entrega
const pedirCancioneDelArtista = async () => {
    try {
        const respuesta = await fetch(url)
        const datos = await respuesta.json()
        console.log(datos)
        if(datos.length !== 0){
            datos.forEach(cancion => {
                isGray ?
                    contenidoHTML += `<div class="fila gray">
                                    <div class="columna"><a href=/songs/${cancion.id}>${cancion.name}</a></div>
                                    <div class="columna">${cancion.clasificacion}</div>
                                    <div class="columna">
                                        <button class="btn-delete">Delete</button>
                                    </div>
                              </div>`
                    :

                    contenidoHTML += `<div class="fila white">
                                    <div class="columna"><a href=/songs/${cancion.id}>${cancion.name}</a></div>
                                    <div class="columna">${cancion.clasificacion}</div>
                                    <div class="columna">
                                        <button class="btn-delete">Delete</button>
                                    </div>
                              </div>`

                isGray = !isGray
            })
            cuerpo.innerHTML = contenidoHTML
        }else{
            cuerpo.innerHTML = `<p class="no-esta">Parece que a ${artista} le falta garage, no esta en esta app under!</p>`
        }

    } catch (error) {
        console.log(error)
    }
}
let formBusqueda = document.querySelector(".form-buscar")

formBusqueda.addEventListener("submit",(e)=>{
    e.preventDefault()
    formBusqueda.input_artista.value.length != 0 ? window.location.href = `/search/${formBusqueda.input_artista.value}` : alert("No puede buscar algo que no escribió, doh... 🤪")
})
pedirCancioneDelArtista()

