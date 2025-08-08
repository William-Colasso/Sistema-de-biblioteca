document.addEventListener("DOMContentLoaded", () => {
    preencherLivros()
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
                    `${user.imagemCapa}`
                );
            } else {
                option.setAttribute(
                    "data-img",
                    `data:image/png;base64,${user.imagemCapa}`
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