 document.addEventListener('DOMContentLoaded', () => {

            if (isLogged()){
                window.location ="/profile"
            }

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
                        nome: form.querySelector('input[name="name"]').value,
                        email: form.querySelector('input[name="email"]').value,
                        telefone: form.querySelector('input[name="phone"]').value,
                        password: form.querySelector('input[name="password"]').value,
                        bibliotecario: document.getElementById("isBibliotecario").checked
                    };

                    console.log('Usuário válido:', user);

                    cadastrar(user);


                    // Aqui você pode enviar os dados manualmente ou deixar o formulário enviar normalmente:
                    // form.submit();
                });
        });


        async function cadastrar(user) {
            const RESPONSE = await fetch("/user/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(user),
            });

            const RESPONSEDATA = await RESPONSE.json();

            alert("Cadastro feito com sucesso!")
            document.getElementById("chk").checked = false;
        }

        document.getElementById("submitLogin").addEventListener('click', ((event) => login(event)))

        async function login(event) {
            event.preventDefault();
            const userDTO = {
                email: document.getElementById("loginEmail").value,
                password: document.getElementById("loginPassword").value,
            };

            try {
                const RESPONSE = await fetch("/user/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(userDTO),
                });

                // Aqui você pega o status da resposta
                const statusCode = RESPONSE.status;

                // Pega os dados do corpo (se houver)
                const data = await RESPONSE.json();

                // Verifica status

                if (statusCode === 200) {
                    alert("Login feito com sucesso!");

                    localStorage.setItem("idUser", data.idUser)
                     window.location ="/profile"
                    // Redirecionamento, armazenamento de token, etc.
                } else if (statusCode === 404) {
                    alert("Email não encontrado");
                } else if (statusCode === 401) {
                    alert("Senha incorreta");
                } else {
                    alert(`Erro inesperado (status ${statusCode})`);
                }
            } catch (error) {
                console.error("Erro na requisição:", error);
                alert("Erro ao tentar fazer login. Verifique sua conexão.");
            }
        }

