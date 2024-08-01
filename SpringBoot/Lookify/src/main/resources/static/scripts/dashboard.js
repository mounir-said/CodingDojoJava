console.log("js vinculado")

//Para renderizar el listado de canciones
let cuerpo = document.querySelector(".cuerpo")
let contenidoHTML = ""
let isGray = true

const pedirTodasLasCanciones = async () =>{
    try{
        const respuesta =  await fetch("/api/canciones")
        const datos = await respuesta.json()
        console.log(datos)
        datos.forEach(cancion => {
            isGray ?
            contenidoHTML += `<div class="fila gray">
                                    <div class="columna"><a href=/songs/${cancion.id}>${cancion.name}</a></div>
                                    <div class="columna">${cancion.clasificacion}</div>
                                    <div class="columna">
                                        <button id="${cancion.id}" class="btn-delete">Delete</button>
                                    </div>
                              </div>`
                :

            contenidoHTML += `<div class="fila white">
                                    <div class="columna"><a href=/songs/${cancion.id}>${cancion.name}</a></div>
                                    <div class="columna">${cancion.clasificacion}</div>
                                    <div class="columna">
                                        <button id="${cancion.id}" class="btn-delete">Delete</button>
                                    </div>
                              </div>`

            isGray = !isGray
        })

        cuerpo.innerHTML = contenidoHTML

        let botones = document.querySelectorAll(".btn-delete")
        botones.forEach(boton =>{
            boton.addEventListener("click",()=>{
                console.log("Eliminar cancion", boton.id)
                const quiereEliminar = window.confirm("¿Desea eliminar el registro "+ boton.id + "?")

                quiereEliminar && eliminarRegistro(boton.id)
            })
        })
    }catch (error){
        console.log(error)
    }
}
//Se invoca la funcion
pedirTodasLasCanciones()

//---------------------------------------------
//Para buscar por nombre de canción
let formBusqueda = document.querySelector(".form-buscar")

formBusqueda.addEventListener("submit",(e)=>{
    e.preventDefault()
    formBusqueda.input_artista.value.length != 0 ? window.location.href = `/search/${formBusqueda.input_artista.value}` : alert("No puede buscar algo que no escribió, doh... 🤪")
})

//Funcion para eliminar registro
const eliminarRegistro = (id) => {
    const requestOptions = {
        method: 'DELETE'
    };

    fetch(`/api/canciones/delete/${id}`, requestOptions)
        .then(response => response.text())
        .then(data => {
            console.log(data);
            alert("registro eliminado")
            window.location.href = '/dashboard'
        })

};
