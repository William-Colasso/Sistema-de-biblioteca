document.addEventListener("DOMContentLoaded", () => {
  let autorC = document.getElementById("autor");
  let bookC = document.getElementById("livro");
    let emprestimoC = document.getElementById("emprestimo")
    
  autorC.addEventListener("click", () => {
    window.location = "/autor/register";
  });

  bookC.addEventListener("click", () => {
    window.location = "/cadastrarLivro";
  });

  emprestimoC.addEventListener("click", ()=>{window.location="/emprestimo/register"})
});
