let ARGUMENTS_API = {
  tituloLivro: "",
  editora: "",
  anoPublicacao: "",
  sinopse: "",
  categoriaLivro: "",
  autor: "",
  quantidadeTotal: 0,
};

function setArguments({
  tituloLivro,
  editora,
  anoPublicacao,
  sinopse,
  categoriaLivro,
  autor,
  quantidadeTotal,
}) {
  ARGUMENTS_API = {
    tituloLivro: checkArgument(tituloLivro),
    editora: checkArgument(editora),
    anoPublicacao: checkArgument(anoPublicacao),
    sinopse: checkArgument(sinopse),
    categoriaLivro: checkArgument(categoriaLivro),
    autor: checkArgument(autor),
    quantidadeTotal: checkArgument(quantidadeTotal),
  };
}

function checkArgument(argument) {
  if (
    argument === undefined ||
    argument === null ||
    String(argument).trim() === "" ||
    String(argument).trim().toLowerCase() === "null"
  ) {
    return null;
  } else {
    return String(argument).replaceAll(" ", "%20");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  let categoria = localStorage.getItem("categoria");
  let titulo = localStorage.getItem("titulo");
  console.log("Categoria: " + categoria);

  if (!categoria && !titulo) {
    search(true);
  } else {
    setArguments({ tituloLivro: titulo, categoriaLivro: categoria });
    setFilters({ tituloLivro: titulo, categoriaLivro: categoria });
    search(false);

    // limpar filtros do localStorage
    localStorage.removeItem("categoria");
    localStorage.removeItem("titulo");
  }

  preencherAutores();
  preencherCategorias();
  preencherEditoras();
  refreshFilters();
});

function refreshFilters() {
  let quantidadeTotal = document.getElementById("quantidadeTotal");
  let quantidadeTotalText = document.getElementById("quantidadeTotalText");
  quantidadeTotal.addEventListener("input", () => {
    quantidadeTotalText.textContent =
      "Quantidade mínima de exemplares: " + quantidadeTotal.value;
  });
}

function setFilters({
  tituloLivro,
  editora,
  anoPublicacao,
  sinopse,
  categoriaLivro,
  autor,
  quantidadeTotal,
}) {
  document.getElementById("tituloLivro").value =
    checkArgument(tituloLivro) ?? document.getElementById("tituloLivro").value;

  document.getElementById("editora").value =
    checkArgument(editora) ?? document.getElementById("editora").value;

  document.getElementById("anoPublicacao").value =
    checkArgument(anoPublicacao) ?? document.getElementById("anoPublicacao").value;

  document.getElementById("sinopse").value =
    checkArgument(sinopse) ?? document.getElementById("sinopse").value;

  document.getElementById("categoriaLivro").value =
    checkArgument(categoriaLivro) ?? document.getElementById("categoriaLivro").value;

  document.getElementById("autor").value =
    checkArgument(autor) ?? document.getElementById("autor").value;

  document.getElementById("quantidadeTotal").value =
    checkArgument(quantidadeTotal) ?? document.getElementById("quantidadeTotal").value;
}

function getFilters() {
  const tituloLivro = document.getElementById("tituloLivro").value;
  const editora = document.getElementById("editora").value;
  const anoPublicacao = document.getElementById("anoPublicacao").value;
  const sinopse = document.getElementById("sinopse").value;
  const categoriaLivro = document.getElementById("categoriaLivro").value;
  const autor = document.getElementById("autor").value;
  const quantidadeTotal = document.getElementById("quantidadeTotal").value;

  setArguments({
    tituloLivro,
    editora,
    anoPublicacao,
    sinopse,
    categoriaLivro,
    autor,
    quantidadeTotal,
  });
}

const verificaCondicoes = (url) => url.includes("=");

async function search(isFilterMode) {
  if (isFilterMode) {
    getFilters();
  }

  console.log("Argumentos para a API: " + JSON.stringify(ARGUMENTS_API));
  let URL = "/book/buscar?";

  if (ARGUMENTS_API.tituloLivro != null) {
    URL += "titulo=" + ARGUMENTS_API.tituloLivro;
  }
  if (ARGUMENTS_API.anoPublicacao != null) {
    if (verificaCondicoes(URL)) {
      URL += "&dataPublicacao=" + ARGUMENTS_API.anoPublicacao;
    } else {
      URL += "dataPublicacao=" + ARGUMENTS_API.anoPublicacao;
    }
  }
  if (ARGUMENTS_API.editora != null) {
    if (verificaCondicoes(URL)) {
      URL += "&editora=" + ARGUMENTS_API.editora;
    } else {
      URL += "editora=" + ARGUMENTS_API.editora;
    }
  }
  if (ARGUMENTS_API.autor != null) {
    if (verificaCondicoes(URL)) {
      URL += "&idAutor=" + ARGUMENTS_API.autor;
    } else {
      URL += "idAutor=" + ARGUMENTS_API.autor;
    }
  }
  if (ARGUMENTS_API.categoriaLivro != null) {
    if (verificaCondicoes(URL)) {
      URL += "&categoria=" + ARGUMENTS_API.categoriaLivro;
    } else {
      URL += "categoria=" + ARGUMENTS_API.categoriaLivro;
    }
  }
  if (ARGUMENTS_API.sinopse != null) {
    if (verificaCondicoes(URL)) {
      URL += "&sinopse=" + ARGUMENTS_API.sinopse;
    } else {
      URL += "sinopse=" + ARGUMENTS_API.sinopse;
    }
  }
  if (ARGUMENTS_API.quantidadeTotal != null) {
    if (verificaCondicoes(URL)) {
      URL += "&quantidadeTotal=" + ARGUMENTS_API.quantidadeTotal;
    } else {
      URL += "quantidadeTotal=" + ARGUMENTS_API.quantidadeTotal;
    }
  }

  console.log("URL: " + URL);

  const RESPONSE = await fetch(URL, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  let arrayBooks = await RESPONSE.json();

  if (Array.isArray(arrayBooks)) {
    const containerLivros = document.getElementById("containerLivros");
    containerLivros.innerHTML = "";
    arrayBooks.forEach((livro) => {
      let div = document.createElement("div");
      let img = document.createElement("img");
      let p = document.createElement("p");
      div.setAttribute("class", "cardLivro");

      if (String(livro.imagemCapa).includes("http")) {
        img.setAttribute("src", `${livro.imagemCapa}`);
      } else {
        img.setAttribute("src", `data:image/png;base64,${livro.imagemCapa}`);
      }
      p.innerText = `${livro.titulo}`;

      div.appendChild(img);
      div.appendChild(p);

      div.addEventListener("click", () => {
        window.location.href =
          "/bookPage?titulo=" + encodeURIComponent(livro.titulo);
      });

      containerLivros.appendChild(div);
    });
  }
}

function cleanFilters() {
  document.querySelectorAll("input, select").forEach((input) => {
    input.value = "";
  });

  document.getElementById("quantidadeTotal").value = 1;
}
