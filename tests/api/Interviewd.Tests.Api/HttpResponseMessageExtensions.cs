using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace Interviewd.Tests.Api
{
    public static class HttpResponseMessageExtensions
    {
        public static async Task<T> GetContent<T>(this HttpResponseMessage httpResponseMessage)
        {
            var content = await httpResponseMessage.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<T>(content);
        }

        public static async Task<T> AwaitGetSuccessfulResponse<T>(this Task<HttpResponseMessage> httpResponseMessageTask)
        {
            return await (await httpResponseMessageTask)
                .GetSuccessfulResponse<T>();
        }

        public static async Task<T> GetSuccessfulResponse<T>(this HttpResponseMessage httpResponseMessage)
        {
            return await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<T>();
        }
    }
}