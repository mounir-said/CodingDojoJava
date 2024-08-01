console.log("agregar js vinculado")
const form = document.querySelector(".form-agregar-cancion")
const url = '/api/canciones'
let objeto = {
    name: "",
    artista: "",
    clasificacion: 1
}


const cargarCancion = async () => {
    objeto.name = form.nombre.value
    objeto.artista = form.artista.value
    objeto.clasificacion = form.clasificacion.value

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objeto)
    };

    console.log("objeto:", objeto);

    try {
        const respuesta = await fetch(url, requestOptions);
        const dato = await respuesta.json();
        console.log(dato)

    } catch (error) {
        console.log("Error en la solicitud:", error);
    }
}

form.addEventListener("submit", (e) => {
    e.preventDefault()
    if (form.nombre.value.length < 5 || form.artista.value.length < 5 || Number(form.clasificacion.value) < 1 || Number(form.clasificacion.value) > 10) { //Hay que asegurarse los tipos de datos con javascript porque te mete strings
        console.log("Hay errores")
        if (form.nombre.value.length < 5) {
            document.querySelector(".error1").style.display = "block";
        }

        if (form.artista.value.length < 5) {
            document.querySelector(".error2").style.display = "block";
        }

        if (form.clasificacion.value < 1 || form.clasificacion.value > 10) {
            document.querySelector(".error3").style.display = "block";

        }
        return
    }
    cargarCancion()
    window.location.href = '/dashboard'
})