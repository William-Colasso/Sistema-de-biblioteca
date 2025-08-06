document.addEventListener("DOMContentLoaded", async ()=>{
    let containerCards = document.getElementById('containerCards')

    const response = await fetch("/categoria", { method: "GET" });
    if (!response.ok) throw new Error("Erro ao buscar autores.");
  
    const categorias = await response.json();

    categorias.forEach((categoria) => {
        let div = document.createElement("div")
        div.setAttribute("class", "cardCategoria")
        div.innerHTML = `<p>${categoria}</p>`
        containerCards.appendChild(div)
    });
})