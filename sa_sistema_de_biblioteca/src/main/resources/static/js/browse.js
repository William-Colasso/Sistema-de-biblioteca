
let ARGUMENTS_API = {


  tituloLivro: "",
  editora: "",
  anoPublicacao: "",
  sinopse: "",
  categoriaLivro: "",
  autor: "",
  quantidadeTotal: 0,
};


function setArguments(tituloLivro, editora, anoPublicacao, sinopse, categoriaLivro, autor, quantidadeTotal) {
  ARGUMENTS_API = {


    tituloLivro: String(tituloLivro).trim() == "" ? null : String(tituloLivro).replaceAll(" ", "%20"),
    editora: String(editora).trim() == "null" ? null : editora,
    anoPublicacao: ((String(anoPublicacao).trim() == "null") || (String(anoPublicacao).trim() == ""))  ? null : anoPublicacao,
    sinopse: String(sinopse).trim() == "" ? null : sinopse,
    categoriaLivro: String(categoriaLivro).trim() == "null" ? null : categoriaLivro,
    autor: String(autor).trim() == "null" ? null : autor,
    quantidadeTotal: quantidadeTotal == 1 ? null : quantidadeTotal,
  };
}

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



  setArguments(tituloLivro, editora, anoPublicacao, sinopse, categoriaLivro, autor, quantidadeTotal)


}

function verificaCondicoes(url){
  if(url.includes("=")){
    return true;
  }
  return false;
}

async function search() {
  getFilters()


  console.log("Argumentos para a API: " + JSON.stringify(ARGUMENTS_API))
  let URL = "/book/buscar?"
  if (ARGUMENTS_API.tituloLivro != null) {
    URL += "titulo=" +
      ARGUMENTS_API.tituloLivro
  }
  if (ARGUMENTS_API.anoPublicacao != null) {
    if(verificaCondicoes(URL)){
      URL += "&dataPublicacao=" +
      ARGUMENTS_API.anoPublicacao
    }
    URL += "dataPublicacao=" +
      ARGUMENTS_API.editora
  }
  if (ARGUMENTS_API.editora != null) {
    if(verificaCondicoes(URL)){
      URL += "&editora=" +
      ARGUMENTS_API.editora
    }
    URL += "editora=" +
      ARGUMENTS_API.editora
  }
  if (ARGUMENTS_API.autor != null) {
    if(verificaCondicoes(URL)){
      URL += "&idAutor=" +
      ARGUMENTS_API.autor
    }
    URL += "idAutor=" +
    ARGUMENTS_API.autor
  }
  if (ARGUMENTS_API.categoriaLivro != null) {
    if(verificaCondicoes(URL)){
      URL += "&categoria=" +
      ARGUMENTS_API.categoriaLivro
    }
    URL += "categoria=" +
    ARGUMENTS_API.categoriaLivro
  }
  if (ARGUMENTS_API.sinopse != null) {
    if(verificaCondicoes(URL)){
      URL += "&sinopse=" +
      ARGUMENTS_API.sinopse
    }
    URL += "sinopse=" +
    ARGUMENTS_API.sinopse
  }
  if (ARGUMENTS_API.quantidadeTotal != null) {
    if(verificaCondicoes(URL)){
      URL += "&quantidadeTotal=" +
      ARGUMENTS_API.quantidadeTotal
    }
    URL += "quantidadeTotal=" +
    ARGUMENTS_API.quantidadeTotal
  }



  console.log("URL: " + URL)

  const RESPONSE = await fetch(URL, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  let arrayBooks = await RESPONSE.json()


  if (Array.isArray(arrayBooks)) {
    const containerLivros = document.getElementById("containerLivros")
    containerLivros.innerHTML = ""
    arrayBooks.forEach((livro) => {
      let div = document.createElement("div")
      let img = document.createElement("img")
      let p = document.createElement("p")
      div.setAttribute("class", "cardLivro")
      if (String(livro.imagemCapa).includes("http")) {

        img.setAttribute("src", `${livro.imagemCapa}`)
      } else {

        img.setAttribute("src", `data:image/png;base64,${livro.imagemCapa}`)
      }
      p.innerText = `${livro.titulo}`

      div.appendChild(img)
      div.appendChild(p)
      containerLivros.appendChild(div)

    })
  }

  if (Array.isArray(arrayBooks)) {
    const containerLivros = document.getElementById("containerLivros")
    containerLivros.innerHTML = ""
    arrayBooks.forEach((livro) => {
      let div = document.createElement("div")
      let img = document.createElement("img")
      let p = document.createElement("p")
      div.setAttribute("class", "cardLivro")
      if (String(livro.imagemCapa).includes("http")) {

        img.setAttribute("src", `${livro.imagemCapa}`)
      } else {

        img.setAttribute("src", `data:image/png;base64,${livro.imagemCapa}`)
      }
      p.innerText = `${livro.titulo}`

      div.appendChild(img)
      div.appendChild(p)
      containerLivros.appendChild(div)

    })
  }
  console.log(arrayBooks)


}

function cleanFilters() {
  document.querySelectorAll("input, select").forEach((input) => {
    input.value = "";
  });
}
