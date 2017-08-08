using System.Net.Http;
using System.Threading.Tasks;
using Interviewd.Common;
using Ploeh.SemanticComparison;
using Ploeh.SemanticComparison.Fluent;

namespace Interviewd.Tests.Api
{
    public static class SemanticComparisonExtensions
    {
        public static async Task<Likeness<T, T>> GetLikenessContent<T>(this HttpResponseMessage httpResponseMessage) where T : IIdentifiable
        {
            var content = await httpResponseMessage.GetContent<T>();
            return content
                .AsSource()
                .OfLikeness<T>()
                .Without(t => t.Id);
        }
    }
}