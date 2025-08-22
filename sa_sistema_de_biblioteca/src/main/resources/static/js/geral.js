document.getElementById("lastItemNav").addEventListener("click", (e)=>{
  e.preventDefault()
  alert("Indo para ... /user/libraria")
  window.location.href = "user/libraria"
})


let tomSelectInstance;
searchByHeader();
aLogged();
function isLogged() {
  return !Number.isNaN(Number.parseInt(localStorage.getItem("idUser")));
}

document.getElementById("lastItemNav").addEventListener("click", (ev)=>{
  ev.preventDefault()
  isBibliotecario =(localStorage.getItem("isBibliotecario"))

   if(isBibliotecario == "true"){
      window.location = "user/libraria"
   }else{
      alert("Você não é bibliotecário!")
   }

})

function aLogged() {
  let content = ";";
  let url = "";
  content = isLogged() ? "Ver perfil " : "Criar conta ";
  url += isLogged() ? "/profile" : "/user/account";
  content += ' <i class="fa-solid fa-user"></i>';

  let anchor = document.getElementById("account");

  anchor.innerHTML = content;
  anchor.setAttribute("href", url);
}

function searchByHeader() {
  let divHeader = document.getElementById("header-actions");

  let input = divHeader.querySelector("input");

  input.addEventListener("focus", () => {
    document.addEventListener("keypress", (ev) => {
      if (ev.key === "Enter") {
        let titulo = input.value.trim(); // pega valor digitado

        if (titulo !== "") {
          localStorage.setItem("titulo", titulo);
          window.location = "/browse";
        }
      }
    });
  });

  let button = divHeader.querySelector("button");

  button.addEventListener("click", () => {
    let titulo = String(input.value).trim();
    if (titulo != "") {
      localStorage.setItem("titulo", titulo);
      window.location = "/browse";
    }
  });
}

async function preencherCategorias() {
  const response = await fetch("/categoria", { method: "GET" });
  if (!response.ok) throw new Error("Erro ao buscar autores.");

  const categorias = await response.json();

  const select = document.getElementById("categoriaLivro");
  console.log("Response: " + response);
  console.log("Select: " + select);
  categorias.forEach((categoria) => {
    const option = document.createElement("option");
    option.value = categoria;
    option.textContent = categoria;

    select.appendChild(option);
  });
}

async function preencherEditoras() {
  const response = await fetch("/book/editoras", { method: "GET" });
  if (!response.ok) throw new Error("Erro ao buscar autores.");

  const editoras = await response.json();

  const select = document.getElementById("editora");
  console.log("Response: " + response);
  console.log("Select: " + select);
  editoras.forEach((editora) => {
    const option = document.createElement("option");
    option.value = editora;
    option.textContent = editora;

    select.appendChild(option);
  });
}

async function preencherAutores() {
  try {
    const response = await fetch("/autor/all", { method: "GET" });
    if (!response.ok) throw new Error("Erro ao buscar autores.");

    const autores = await response.json();
    const select = document.getElementById("autor");

    // Criação das opções com imagem base64
    autores.forEach((autor) => {
      const option = document.createElement("option");
      option.value = autor.idAutor;
      option.textContent = autor.nomeAutor;
      if (String(autor.fotoAutor).includes("http")) {
        option.setAttribute("data-img", `${autor.fotoAutor}`);
      } else {
        option.setAttribute(
          "data-img",
          `data:image/png;base64,${autor.fotoAutor}`
        );
      }

      select.appendChild(option);
    });

    // Inicialização do TomSelect
    new TomSelect("#autor", {
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
    console.error("Erro ao buscar autores:", error);
  }
}
