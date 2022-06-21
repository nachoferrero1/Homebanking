const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            cliente:[],
            tarjetas:[],
            color:"",
            tipo:""
        
            
        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        axios.get(`/api/clients/current`)
            .then(data => {
                this.cliente = data.data
                console.log(this.cliente)
                this.tarjetas = this.cliente.cards
                console.log(this.tarjetas)
                console.log(this.color)
                console.log(this.tipo)
            })


    },


    methods: {

        crearTarjeta(){
            axios.post('/api/clients/current/cards', `color=${this.color}&type=${this.tipo}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                console.log('cardCreated')
                Swal.fire({
                    title: "tarjeta creada Exitosamente",
                    text: "te mostraremos tu nueva tarjeta en la seccion de tarjetas",
                    icon: "success",
                    confirmButtonText: "ok",
                    width: "30%",
                    })
                    .then(response => 
                        window.location.href = './cards.html'
                        )
                })
            .catch(response => {
                console.log("error")
                Swal.fire({
                    title: "error al crear",
                    text: "solo puedes tener 3 tarjetas por tipo",
                    icon: "error",
                    confirmButtonText: "ok",
                    width: "30%",
                    })
            })
        },

        volver() {
            axios.post('/api/logout')
                .then(response => {
                    setTimeout(function () {
                        window.location.href = './index.html'
                    }, 1000)
                })
        },
    },



    computed: {
    },
    

}).mount('#app')


