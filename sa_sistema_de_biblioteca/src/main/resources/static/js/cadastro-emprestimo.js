document.addEventListener("DOMContentLoaded", () => {
    preencherLivros()
    preencherUsuarios()
})

async function preencherLivros() {
    try {
        const response = await fetch("/book", { method: "GET" });
        if (!response.ok) throw new Error("Erro ao buscar autores.");

        const autores = await response.json();
        const select = document.getElementById("livro");

        // Criação das opções com imagem base64
        autores.forEach((livro) => {
            const option = document.createElement("option");
            option.value = livro.idLivro;
            option.textContent = livro.titulo;
            if (String(livro.imagemCapa).includes("http")) {
                option.setAttribute(
                    "data-img",
                    `${livro.imagemCapa}`
                );
            } else {
                option.setAttribute(
                    "data-img",
                    `data:image/png;base64,${livro.imagemCapa}`
                );
            }

            select.appendChild(option);
        });

        // Inicialização do TomSelect
        new TomSelect("#livro", {
            render: {
                option: function (data, escape) {
                    return `
                <div class="option-with-image">
                  <img src="${escape(data.img)}" alt="${escape(
                        data.text
                    )}" style="width:30px;height:30px;border-radius:50%;margin-right:8px;">
                  <span>${escape(data.text)}</span>
                </div>`;
                },
                item: function (data, escape) {
                    return `
                <div class="option-with-image">
                  <img src="${escape(data.img)}" alt="${escape(
                        data.text
                    )}" style="width:20px;height:20px;border-radius:50%;margin-right:6px;">
                  <span>${escape(data.text)}</span>
                </div>`;
                },
            },
            onInitialize() {
                this.options = Object.fromEntries(
                    [...this.select.options].map((opt) => [
                        opt.value,
                        {
                            value: opt.value,
                            text: opt.textContent,
                            img: opt.getAttribute("data-img"),
                        },
                    ])
                );
            },
        });
    } catch (error) {
        console.error("Erro ao buscar livros:", error);
    }
}

async function preencherUsuarios() {
    try {
        const response = await fetch("/user", { method: "GET" });
        if (!response.ok) throw new Error("Erro ao buscar autores.");

        const autores = await response.json();
        const select = document.getElementById("user");

        // Criação das opções com imagem base64
        autores.forEach((user) => {
            const option = document.createElement("option");
            option.value = user.idUser;
            option.textContent = user.nome;
            if (String(user.imagemCapa).includes("http")) {
                option.setAttribute(
                    "data-img",
                    `${"https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg"}`
                );
            } else {
                option.setAttribute(
                    "data-img",
                    `${"https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg"}`
                );
            }

            select.appendChild(option);
        });

        // Inicialização do TomSelect
        new TomSelect("#user", {
            render: {
                option: function (data, escape) {
                    return `
                <div class="option-with-image">
                  <img src="${escape(data.img)}" alt="${escape(
                        data.text
                    )}" style="width:30px;height:30px;border-radius:50%;margin-right:8px;">
                  <span>${escape(data.text)}</span>
                </div>`;
                },
                item: function (data, escape) {
                    return `
                <div class="option-with-image">
                  <img src="${escape(data.img)}" alt="${escape(
                        data.text
                    )}" style="width:20px;height:20px;border-radius:50%;margin-right:6px;">
                  <span>${escape(data.text)}</span>
                </div>`;
                },
            },
            onInitialize() {
                this.options = Object.fromEntries(
                    [...this.select.options].map((opt) => [
                        opt.value,
                        {
                            value: opt.value,
                            text: opt.textContent,
                            img: opt.getAttribute("data-img"),
                        },
                    ])
                );
            },
        });
    } catch (error) {
        console.error("Erro ao buscar livros:", error);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formEmprestimo");
  
    form.addEventListener("submit", async (event) => {
      event.preventDefault();
  
      const userId = document.getElementById("user").value;
      const livroId = document.getElementById("livro").value;
      const dataEmprestimo = document.getElementById("dataEmprestimo").value;
      const dataDevolucaoPrevista = document.getElementById("dataDevolucaoPrevista").value;
      const devolvido = document.getElementById("devolvido").value === "true";
  
      if (!userId || !livroId || !dataEmprestimo || !dataDevolucaoPrevista) {
        alert("Por favor, preencha todos os campos obrigatórios.");
        return;
      }
  
      const emprestimo = {
        user: { idUser: Number(userId) },
        livro: { idLivro: Number(livroId) },
        dataEmprestimo,
        dataDevolucaoPrevista,
        devolvido
      };
  
      try {
        const response = await fetch("/loan/create", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(emprestimo),
        });
  
        if (response.ok) {
          alert("Empréstimo cadastrado com sucesso!");
          form.reset();
        } else {
          const errorData = await response.json();
          alert("Erro ao cadastrar empréstimo: " + (errorData.message || response.statusText));
        }
      } catch (error) {
        alert("Erro na requisição: " + error.message);
        console.error(error);
      }
    });
  });
  