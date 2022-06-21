const app = Vue.createApp({

    //CREAR Y USAR VARIABLES
    data() {
        return {
            message: `Hello vue`,
            dataClient:{},
            accountsClient:[],
            infoClient:"", 

            emailR:"",
            passwordR:"",
            firstNameR:"",
            lastNameR:"", 
        }
    },

    created(){

    },


    methods: {

        register(){

            axios.post('/api/clients',`firstName=${this.firstNameR}&lastName=${this.lastNameR}&email=${this.emailR}&password=${this.passwordR}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>
                console.log('registered'))

            .then(response => {
            axios.post('/api/login',`email=${this.emailR}&password=${this.passwordR}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>
                console.log('signed in!!!'))

            setTimeout(function(){
                window.location.href = './entrada.html'
                },1000)})

        },



    },

    computed: {

    },
    

}).mount('#app')


