const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            cliente: [],
            cuentas: [],
            payments: "",
            loanType: "",
            account: "",
            cuentaDestino: "",
            monto: "",
            destination: "",
            loans: "",

            name:"",
            payments:"",
            maxAmount:""

        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        axios.get(`/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                console.log(this.cliente)
                this.cuentas = this.cliente.accounts
                console.log(this.cuentas)
                this.tarjetas = this.cliente.cards
            })
            .then(() => {   /* funcion vacia */
                axios.get("/api/loans")
                    .then(response => {
                        this.loans = response.data
                    })

            })
    },


    methods: {

        createLoan() {
            axios.post(`/api/loan?name=${this.name}&payments=${this.payments}&maxAmount=${this.maxAmount}`)
                .then(response => { 
                    console.log('successful loan')
                Swal.fire({
                title: "Solicitud Exitosa",
                text: "podras ver tu nuevo prestamo creado en la seccion de prestamos",
                icon: "success",
                confirmButtonText: "ok",
                width: "30%",
                })
                .then(() => {
                        window.location.href = './accounts.html'
                })
            })
                .catch(response => {
                    console.log('error')
                    Swal.fire({
                        title: "solicitud fallida",
                        text: "intenta nuevamente",
                        icon: "error",
                        confirmButtonText: "intentar de nuevo",
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


