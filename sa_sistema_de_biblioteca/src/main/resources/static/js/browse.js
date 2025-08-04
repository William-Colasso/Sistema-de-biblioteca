document.addEventListener("DOMContentLoaded", () => {
  preencherAutores();
  preencherCategorias();
  preencherEditoras();

  refreshFilters();
});

function refreshFilters() {
  quantidadeTotal = document.getElementById("quantidadeTotal");
  quantidadeTotalText = document.getElementById("quantidadeTotalText");
  quantidadeTotal.addEventListener("input", () => {
    quantidadeTotalText.textContent =
      "Quantidade mínima de exemplares:" + quantidadeTotal.value;
  });
}

function getFilters() {
  const tituloLivro = document.getElementById("tituloLivro").value;
  const editora = document.getElementById("editora").value;
  const anoPublicacao = document.getElementById("anoPublicacao").value;
  const sinopse = document.getElementById("sinopse").value;
  const categoriaLivro = document.getElementById("categoriaLivro").value;
  const autor = document.getElementById("autor").value;
  const quantidadeTotal = document.getElementById("quantidadeTotal").value;

  const ARGUMENTS_API = {
    tituloLivro: String(tituloLivro).trim() == "" ? null : tituloLivro,
    editora: String(editora).trim() == "" ? null : editora,
    anoPublicacao: String(anoPublicacao).trim() == "" ? null : anoPublicacao,
    sinopse: String(sinopse).trim() == "" ? null : sinopse,
    categoriaLivro: String(categoriaLivro).trim() == "" ? null : categoriaLivro,
    autor: String(autor).trim() == "" ? null : autor,
    quantidadeTotal: quantidadeTotal == 1 ? null : quantidadeTotal,
  };

  return ARGUMENTS_API;
}

async function search(ARGUMENTS_API) {
  URL =
    "/book/buscar?titulo=" +
    ARGUMENTS_API.tituloLivro +
    "&editora=" +
    ARGUMENTS_API.editora +
    "&idAutor=" +
    ARGUMENTS_API.autor +
    "&idCategoria=" +
    ARGUMENTS_API.categoriaLivro;

  const RESPONSE = await fetch(URL, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  let arrayBooks = await RESPONSE.json()

  console.log(arrayBooks)


}

function cleanFilters() {
  document.querySelectorAll("input, select").forEach((input) => {
    input.value = "";
  });
}
