console.log("js detail vinculado")

const pathname = window.location.pathname;
const parts = pathname.split('/');
const id = parts[2];

console.log("Se busca por artista con", id);
let url = `/api/canciones/${id}`
console.log(url)
fetch(url)
    .then(response => response.json())
    .then(cancion => {
        console.log(cancion);
        document.querySelector(".tit_can").textContent = cancion.name
        document.querySelector(".art_can").textContent = cancion.artista
        document.querySelector(".clas_can").textContent = cancion.clasificacion

        document.querySelector(".btn-delete").addEventListener("click",() => {
            console.log("eliminar",id)
            const quiereEliminar = window.confirm("¿Desea eliminar "+ cancion.name + "?")

            quiereEliminar && eliminarRegistro(id)
        })
    })

//Repito esta funcion pero en el refactor voy a usar type module para hacerlo prolijo
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