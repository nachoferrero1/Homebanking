const app = Vue.createApp({

    //CREAR Y USAR VARIABLES        
    data() {
        return {
            message:`Hello world`,
            clientes: [],
            cliente:{},
            Json: [],

            firstName: "",
            lastName: "",
            email: "",
        }
    },

    created (){
        axios.get(`http://localhost:8080/clients`)
        .then(data => {
            this.clientes = data.data._embedded.clients
            this.Json = data.data
            console.log(this.Json)
            console.log(this.clientes)
        })
    },


    methods () {
        
    },

    computed () {

    },

}).mount('#app')


