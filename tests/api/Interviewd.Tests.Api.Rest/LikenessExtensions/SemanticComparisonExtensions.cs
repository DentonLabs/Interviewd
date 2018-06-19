using System.Net.Http;
using System.Threading.Tasks;
using Interviewd.Common;
using SemanticComparison;
using SemanticComparison.Fluent;

namespace Interviewd.Tests.Api.Rest.LikenessExtensions
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

        public static Likeness<T, T> ToLikeness<T>(this T value, bool compareId = false) where T : IIdentifiable
        {
            var likeness = value.AsSource().OfLikeness<T>();

            if (!compareId)
            {
                likeness = likeness.Without(o => o.Id);
            }

            return likeness;
        }
    }
}