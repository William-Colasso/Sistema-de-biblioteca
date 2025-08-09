
        async function getUsuario(ID_USER) {
            const RESPONSE = await fetch("/user/get?idUser=" + ID_USER, {
                method: "GET",
            });
            const statusCode = RESPONSE.status;
            let jsonR = await RESPONSE.json()

            if (statusCode == 200) {
                

                document.querySelector('[name="name"]').value = jsonR.nome
                document.querySelector('[name="email"]').value = jsonR.email
                document.querySelector('[name="phone"]').value = jsonR.telefone
                document.querySelector('[name="password"]').value = jsonR.password
                document.getElementById("isBibliotecario").checked = jsonR.bibliotecario
                 localStorage.setItem("isBibliotecario", document.getElementById("isBibliotecario").checked)
            }
            console.log(jsonR)
        }
        document.addEventListener('DOMContentLoaded', () => {

            const ID_USER = localStorage.getItem("idUser")
            

            if (Number.isNaN(Number.parseInt(ID_USER))) {
                window.location = "/home"
            }

            getUsuario(ID_USER)





            const validator = new JustValidate('#signup-form');

            validator
                .addField('input[name="name"]', [
                    {
                        rule: 'required',
                        errorMessage: 'Nome é obrigatório',
                    },
                    {
                        rule: 'minLength',
                        value: 3,
                        errorMessage: 'Nome muito curto',
                    }
                ])
                .addField('input[name="email"]', [
                    {
                        rule: 'required',
                        errorMessage: 'E-mail é obrigatório',
                    },
                    {
                        rule: 'email',
                        errorMessage: 'E-mail inválido',
                    }
                ])
                .addField('input[name="password"]', [
                    {
                        rule: 'required',
                        errorMessage: 'Senha é obrigatória',
                    },
                    {
                        rule: 'minLength',
                        value: 6,
                        errorMessage: 'A senha deve ter pelo menos 6 caracteres',
                    }
                ])
                .onSuccess((event) => {
                    event.preventDefault();

                    const form = document.getElementById('signup-form');
                    
                    const user = {
                        idUser: ID_USER,
                        nome: form.querySelector('input[name="name"]').value,
                        email: form.querySelector('input[name="email"]').value,
                        telefone: form.querySelector('input[name="phone"]').value,
                        password: form.querySelector('input[name="password"]').value,
                        bibliotecario: document.getElementById("isBibliotecario").checked
                    };

                    console.log('Usuário válido:', user);

                    editar(user);

                    

                    window.location.reload()
                    // Aqui você pode enviar os dados manualmente ou deixar o formulário enviar normalmente:
                    // form.submit();
                });
        });


        async function editar(user) {
            const RESPONSE = await fetch("/user/" + user.idUser, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(user),
            });

            const RESPONSEDATA = await RESPONSE.json();

            localStorage.setItem("isBibliotecario", RESPONSEDATA.bibliotecario)

            alert("Perfil salvo com sucesso!")
            document.getElementById("chk").checked = false;

            

        }

        function changeStateInputs() {
            let inputsArr = Array.from(document.querySelectorAll("input")).concat(Array.from(document.querySelectorAll("button")))

            inputsArr.forEach((input) => {
                input.disabled = !input.disabled
            })
        }


        function logOut(){
            localStorage.clear()
            window.location.reload()
        }