const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            cuenta:[],
            transacciones:[],
            
            cliente:[],
            cuentas:[]
            
        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        console.log(id)
        axios.get("/api/accounts/"+id) 
            .then(data => {
                this.cuenta = data.data
                console.log(this.cuenta)
                this.transacciones = this.cuenta.transactions
                console.log(this.transacciones)
                this.transacciones = this.transacciones.sort((b, a) => a.id - b.id)
            })
            axios.get(`/api/clients/current`)
            .then(data => {
                this.cliente = data.data
                console.log(this.cliente)
                this.cuentas = this.cliente.accounts
                console.log(this.cuentas)
                
            })
    },

    


    methods: {

        volver(){
            setTimeout(function(){
                window.location.href = './index.html'
                },1000)
        }

    },

    computed: {

    },
    

}).mount('#app')


