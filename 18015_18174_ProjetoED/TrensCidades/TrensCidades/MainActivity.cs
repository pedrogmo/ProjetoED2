using Android.App;
using Android.Widget;
using Android.OS;
using TrensCidades.Classes;
using System.IO;
using System.Text;
using Android.Content.Res;
using Android.Util;
using System.Collections.Generic;

namespace TrensCidades
{
    [Activity(Label = "TrensCidades", MainLauncher = true)]
    public class MainActivity : Activity
    {
        //Gustavo Henrique de Meira - 18015
        //Pedro Gomes Moreira - 18174


        Button btnBuscar;
        Spinner spDeOnde, spParaOnde;
        BucketHash<Cidade> bhCidade;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);
            btnBuscar = FindViewById<Button>(Resource.Id.btnBuscar);
            spDeOnde = FindViewById<Spinner>(Resource.Id.spDeOnde);
            spParaOnde = FindViewById<Spinner>(Resource.Id.spParaOnde);

            AssetManager assets = this.Assets;
            
            bhCidade = new BucketHash<Cidade>();
            ArrayAdapter<string> cidadesSpinner = new ArrayAdapter<string>(this, Resource.Layout.item_spinner);
            
            using (StreamReader arq = new StreamReader(assets.Open("cidades.txt"), Encoding.UTF7))
                while (!arq.EndOfStream)
                {
                    string linha = arq.ReadLine();
                    Cidade cd = new Cidade(linha);
                    cidadesSpinner.Add(cd.ToString());
                    bhCidade.Inserir(cd);
                }

            spDeOnde.Adapter = cidadesSpinner;
            spParaOnde.Adapter = cidadesSpinner;


        }
    }
}

