const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            cliente:[],
            cuentas:[],
            tarjetas:[],
            transferType:"",
            account:"",
            cuentaDestino:"",
            monto:"",
            description:"",
        
        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        axios.get(`/api/clients/current`)
            .then(data => {
                this.cliente = data.data
                console.log(this.cliente)
                this.cuentas = this.cliente.accounts
                console.log(this.cuentas)
                this.tarjetas = this.cliente.cards
                /* console.log(this.tarjetas) */
            })
    },


    methods: {

        transferir(){
            console.log(this.monto, this.description, this.account, this.cuentaDestino)
            axios.post('/api/transactions',`amount=${this.monto}&description=${this.description}&numberOrigen=${this.account}&numberDestiny=${this.cuentaDestino}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
                console.log('successful transfer')
                Swal.fire({
                    title: "transferencia Exitosa",
                    text: "podras ver tu nueva cuenta en la seccion de cuentas",
                    icon: "success",
                    confirmButtonText: "ok",
                    width: "30%",
                    })
                })
                .catch(response =>{
                    console.log("error")
                    Swal.fire({
                        title: "Transferencia fallida",
                        text: "vuelve a intentarlo, comprueba que todos los datos son correctos",
                        icon: "error",
                        confirmButtonText: "volver a intentar",
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


