document.addEventListener("DOMContentLoaded", async () => {
    // 1️⃣ Captura o parâmetro "titulo" da URL
    const params = new URLSearchParams(window.location.search);
    const tituloParam = params.get("titulo");

    if (!tituloParam) {
        alert("Nenhum título informado na URL!");
        return;
    }

    try {
        // 2️⃣ Faz a requisição GET para o backend
        const response = await fetch(`/book/relatorio?titulo=${encodeURIComponent(tituloParam)}`, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error(`Erro ao buscar livro: ${response.status}`);
        }

        const livro = await response.json();

        // 3️⃣ Preenche os elementos da página
        document.querySelector(".book-cover").src = livro.imagemCapa
            ? `data:image/jpeg;base64,${livro.imagemCapa}`
            : "/img/placeholder.png"; // imagem padrão caso não tenha capa

        document.querySelector(".card-form h1").textContent = livro.titulo || "Título não informado";
        document.querySelector(".card-form").innerHTML = `
            <h1>${livro.titulo || ""}</h1>
            <p><strong>Autor:</strong> ${livro.autor ? livro.autor.nome : "Não informado"}</p>
            <p><strong>Editora:</strong> ${livro.editora || "Não informado"}</p>
            <p><strong>Data de Publicação:</strong> ${livro.anoPublicacao || "Não informado"}</p>
            <p><strong>Quantidade disponível:</strong> ${livro.quantidadeTotal || 0} unidades</p>
            <h2>Sinopse</h2>
            <p>${livro.sinopse || "Sem sinopse cadastrada."}</p>
        `;

    } catch (error) {
        console.error(error);
        alert("Erro ao carregar os dados do livro.");
    }
});