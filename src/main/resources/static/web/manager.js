const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello world`,
            clientes: [],
            cliente: {},
            Json: [],

            nombre: "",
            apellido: "",
            email: "",
        }
    },

    created() {
        axios.get(`/rest/clients`)
            .then(data => {
                this.clientes = data.data._embedded.clients
                this.Json = data.data
                console.log(this.Json)
                console.log(this.clientes)
            })
    },


    methods: {
        enviarCliente() {
            if (this.nombre != "" && this.apellido != "" && this.email != "" && this.email.includes("@" && ".")) {
                axios.post(`/rest/clients`, {
                    firstName: this.nombre,
                    lastName: this.apellido,
                    email: this.email,
                })
            }


        },

        //INTENTO DE BOTONES EDIT Y DELETE
        borrarCliente(id){
            axios.delete(id)
            .then(function (){
                location.reload()
                .catch(error => console.log(error))
            })
        },

         editarCliente() {
            axios.patch(this.url,{

                nombre: this.nombreTemporal,
                apellido: this.apellidoTemporal,
                email: this.emailTemporal

            })
            .then(response => console.log(response))
            .catch(error => console.log(error))
        }  
    },

        computed: {

    },

}).mount('#app')


