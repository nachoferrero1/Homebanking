const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            email:"",
            password:"",
            dataClient:{},
            accountsClient:[],
            infoClient:"",
            firstName:"",
            lastName:"", 

            emailR:"",
            passwordR:"",
            firstNameR:"",
            lastNameR:"", 
        }
    },

    created(){

    },


    methods: {

        login(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                console.log('signed in!!!')
            setTimeout(function(){
                window.location.href = './entrada.html'
                },1000)
            })
            .catch(response => {
                console.log("error")
                Swal.fire({
                    title: "Login Fallido",
                    text: "Parte de tu información no es correcta. Intentalo de nuevo",
                    icon: "warning",
                    confirmButtonText: "intentar de nuevo",
                    width: "30%",
                })
            })
            
        },

    },

    computed: {

    },
    

}).mount('#app')


