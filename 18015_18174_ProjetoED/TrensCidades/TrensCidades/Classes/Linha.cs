using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace TrensCidades.Classes
{
    class Linha
    {
        private string conteudo;

        public Linha(
            string conteudo)
        {
            Conteudo = conteudo;
        }

        public string Conteudo { get => conteudo; set => conteudo = value; }
    }
}