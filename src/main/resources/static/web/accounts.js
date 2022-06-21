const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            cliente: [],
            cuentas: [],
            prestamos: [],
            accountType:"",
            numero:"",
            mail:""


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
                this.prestamos = this.cliente.loans
                console.log(this.prestamos)
                this.mail = this.cliente.email
                console.log(this.mail)
            })

    },


    methods: {
        crearCuenta() {
            axios.post(`/api/clients/current/accounts?typeAccount=${this.accountType}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                        console.log('accountCreated')
                    Swal.fire({
                        title: "solicitud Exitosa",
                        text: "podras ver tu nueva cuenta en la seccion de cuentas",
                        icon: "success",
                        confirmButtonText: "ok",
                        width: "30%",
                        })
                        .then(() => {
                            window.location.href = './accounts.html'
                    })
                })
                /* .catch(response => {
                    console.log('error')
                    Swal.fire({
                        title: "peticion fallida",
                        text: "alcanzaste el limite de cuentas",
                        icon: "error",
                        confirmButtonText: "volver",
                        width: "30%",
                    })
                }) */
        },

        volver() {
            axios.post('/api/logout')
                .then(response => {
                    setTimeout(function () {
                        window.location.href = './index.html'
                    }, 1000)
                })
        },


        obtenerFecha(fecha) {
            const date = new Date(fecha)
            const year = date.getFullYear()
            const day = date.getDay()
            const month = date.getMonth()
            return day + "/" + (month + 1) + "/" + year
        },

        ocultarBoton() {
            if (this.cuentas.length > 2) {
                return false
            } else {
                return true
            }
        },

        deleteAccount(param){
            axios.patch(`/api/clients/current/accounts?number=${param}`)
                .then(response => {
                        console.log('accountDeleted')
                    Swal.fire({
                        title: "Eliminacion Exitosa",
                        text: "Eliminamos su cuenta exitosamente",
                        icon: "success",
                        confirmButtonText: "ok",
                        width: "30%",
                        })
                        .then(() => {
                            window.location.href = './accounts.html'
                    })
                })
                .catch(response => {
                    console.log("error")
                    Swal.fire({
                        title: "Error",
                        text: "Recuerda Dejar tu cuenta en $0 para poder eliminarla",
                        icon: "warning",
                        confirmButtonText: "ok",
                        width: "30%",
                        })
                })
        }
    },

    computed: {

    },


}).mount('#app')


