const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            cliente:[],
            tarjetas:[],
            tarjetaSeleccionada:""
        
            
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

            })
    },


    methods: {
        obtenerFecha (fecha) {
            const date = new Date(fecha)
            const year = date.getFullYear()
            const year2 = year.toString().substring(2)
            console.log((date.getMonth()+1)+ "/" + year2)
            return (date.getMonth()+1)+ "/" + year2
        },

        obtenerFechaActual (param) {
            let cardDate = new Date(param)
            console.log(cardDate)
            let fecha = new Date();
            console.log(fecha)
            let hoy = fecha.getDate();
            let mesActual = fecha.getMonth() + 1;
            let añoActual = fecha.getFullYear();
            let añoVencimiento = cardDate.getFullYear();
            let mesVencimiento = cardDate.getMonth() + 1;
            console.log(mesVencimiento, mesActual)
            /* console.log(hoy, mesActual, añoActual); */
            console.log(cardDate - fecha)
            if(añoActual > añoVencimiento){
                return true 
            } 
            else if (añoActual == añoVencimiento){
                if(mesActual > mesVencimiento ){
                    return true
                }
                else{
                    return false
                } 
            } else{
                return false
            }
        },

        

        volver(){
            setTimeout(function(){
                window.location.href = './index.html'
                },1000)
        },

        ocultarBoton(){
            if (this.tarjetas.length > 5){
                return false
            } else {
                return true
            }
        },

        eliminarTarjeta(param){
            console.log(param)
            axios.patch(`/api/clients/current/cards?number=${param}`)
            .then(function(){
                console.log("delete succcesfull")
                location.reload()
            })
            .catch(error => 
                console.log("error"))
        }
        
    },

    computed: {

    },
    

}).mount('#app')


