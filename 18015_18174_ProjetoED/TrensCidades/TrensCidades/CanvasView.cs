using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;

namespace TrensCidades
{
    public class CanvasView : View
    {
        private const int mapaTotalX = 717;
        private const int mapaTotalY = 578;

        public CanvasView(Context context) : base(context)
        {
        }

        public CanvasView(Context context, IAttributeSet attrs) : base(context, attrs)
        {
        }

        public CanvasView(Context context, IAttributeSet attrs, int defStyleAttr) : base(context, attrs, defStyleAttr)
        {
        }

        public CanvasView(Context context, IAttributeSet attrs, int defStyleAttr, int defStyleRes) : base(context, attrs, defStyleAttr, defStyleRes)
        {
        }

        protected CanvasView(IntPtr javaReference, JniHandleOwnership transfer) : base(javaReference, transfer)
        {
        }

        protected override void OnDraw(Canvas canvas)
        {
            Bitmap mapa = BitmapFactory.DecodeResource(Resources, Resource.Drawable.Mapa);

            canvas.DrawBitmap(mapa, 0, 0, null);

            base.OnDraw(canvas);
        }
    }
}