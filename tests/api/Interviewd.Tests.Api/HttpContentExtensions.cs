using System.Net.Http;
using System.Text;
using Newtonsoft.Json;

namespace Interviewd.Tests.Api
{
    public static class HttpContentExtensions
    {
        public static StringContent ToStringContent(this object @object)
        {
            return new StringContent(
                JsonConvert.SerializeObject(@object),
                Encoding.UTF8,
                "application/json");
        }
    }
}