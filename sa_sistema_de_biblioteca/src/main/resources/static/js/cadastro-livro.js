document.addEventListener("DOMContentLoaded", async () => {


  preencherCategorias();
  preencherEditoras();
  preencherAutores();
  // Preview da imagem da capa
  document.getElementById("imagemCapa").addEventListener("change", function () {
    const file = this.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
      const preview = document.getElementById("previewCapa");
      preview.src = e.target.result;
      preview.style.display = "block";
      preview.setAttribute("data-base64", e.target.result.split(",")[1]);
    };

    if (file) reader.readAsDataURL(file);
  });

  // Submissão do formulário
  document
    .getElementById("formLivro")
    .addEventListener("submit", async function (e) {
      e.preventDefault();

      const capa =
        document.getElementById("previewCapa").getAttribute("data-base64") ||
        "";

      const livro = {
        titulo: document.getElementById("titulo").value,
        editora: document.getElementById("editora").value,
        anoPublicacao: document.getElementById("anoPublicacao").value,
        sinopse: document.getElementById("sinopse").value,
        imagemCapa: capa,
        categoriaLivro: document.getElementById("categoriaLivro").value,
        autor: {
          idAutor: parseInt(document.getElementById("autor").value),
        },
        quantidadeTotal: parseInt(
          document.getElementById("quantidadeTotal").value
        ),
      };

      try {
        const response = await fetch("/book/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(livro),
        });

        if (response.ok) {
          alert("Livro cadastrado com sucesso!");
          document.getElementById("formLivro").reset();
          document.getElementById("previewCapa").style.display = "none";
        } else {
          alert("Erro ao cadastrar o livro.");
        }
      } catch (error) {
        alert("Erro na requisição.");
        console.error(error);
      }
    });
});
